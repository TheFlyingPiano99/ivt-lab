 package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockTSPrimary;
  private TorpedoStore mockTSSecondary;

  @BeforeEach
  public void init(){
    this.mockTSPrimary = mock(TorpedoStore.class);
    this.mockTSSecondary = mock(TorpedoStore.class);
    this.ship = new GT4500(mockTSPrimary, mockTSSecondary);
    when(mockTSPrimary.fire(1)).thenReturn(true);
    when(mockTSSecondary.fire(1)).thenReturn(true);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockTSPrimary, times(1)).fire(1);
    verify(mockTSSecondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockTSPrimary, times(1)).fire(1);
    verify(mockTSSecondary, times(1)).fire(1);
  }

  @Test
  public void fire_single_twice_success() {
    // Arrange

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    verify(mockTSPrimary, times(1)).fire(1);
    verify(mockTSSecondary, times(1)).fire(1);
  } 
  @Test
  public void fire_single_twice_first_failure() {
    // Arrange
    when(mockTSPrimary.fire(1)).thenReturn(false);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result1);
    assertEquals(true, result2);
    verify(mockTSPrimary, times(1)).fire(1);
    verify(mockTSSecondary, times(1)).fire(1);
  }

  @Test
  public void fire_single_twice_second_failure() {
    // Arrange
    when(mockTSSecondary.fire(1)).thenReturn(false);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(false, result2);
    verify(mockTSPrimary, times(1)).fire(1);
    verify(mockTSSecondary, times(1)).fire(1);
  }

  @Test
  public void fire_single_twice_first_out_of_ammo() {
    // Arrange
    when(mockTSPrimary.isEmpty()).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    verify(mockTSPrimary, times(0)).fire(1);
    verify(mockTSSecondary, times(2)).fire(1);
  }

  @Test
  public void fire_single_twice_second_out_of_ammo() {
    // Arrange
    when(mockTSSecondary.isEmpty()).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    verify(mockTSPrimary, times(2)).fire(1);
    verify(mockTSSecondary, times(0)).fire(1);
  }

  @Test
  public void fire_single_twice_both_out_of_ammo() {
    // Arrange
    when(mockTSPrimary.isEmpty()).thenReturn(true);
    when(mockTSSecondary.isEmpty()).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result1);
    assertEquals(false, result2);
    verify(mockTSPrimary, times(0)).fire(1);
    verify(mockTSSecondary, times(0)).fire(1);
  }

  @Test
  public void fire_all_twice() {
    // Arrange

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.ALL);
    boolean result2 = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    verify(mockTSPrimary, times(2)).fire(1);
    verify(mockTSSecondary, times(2)).fire(1);
  }

  @Test
  public void fire_laser() {

    Boolean result = ship.fireLaser(FiringMode.SINGLE);

    assertEquals(false, result);
  }

}

